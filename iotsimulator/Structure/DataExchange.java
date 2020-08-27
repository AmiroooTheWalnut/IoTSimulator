/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iotsimulator.Structure;

import java.util.TimerTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author user
 */
public class DataExchange implements Comparable {

    public Device fromDevice;
    public Device toDevice;
    public long time;//MILI SECONDS
    public String message;
    public Metric metric;
    public boolean isTriggered;

    ScheduledThreadPoolExecutor realeaseTimer = new ScheduledThreadPoolExecutor(1);
    ScheduledThreadPoolExecutor retryTimer = new ScheduledThreadPoolExecutor(1);

    private DataExchange thisDataExchange = this;

    public DataExchangeResource dataExchangeResource;

    public DataExchange(Device passed_fromDevice, Device passed_toDevice, long passed_time, String passed_message, Metric passed_metric) {
        fromDevice = passed_fromDevice;
        toDevice = passed_toDevice;
        time = passed_time;
        message = passed_message;
        metric = passed_metric;

        double usedBandWidthAddition = message.getBytes().length / Math.pow(2, 10);

        double usedCPUAddition = metric.cPUUsageForEachTransmit;
        double usedMemoryAddition = metric.memoryUsageForEachTransmit + message.getBytes().length / Math.pow(2, 20);
        double usedStorageAddition = metric.storageUsageForTransmit;

        dataExchangeResource = new DataExchangeResource(usedBandWidthAddition, usedCPUAddition, usedMemoryAddition, usedStorageAddition);
    }

    public boolean consumeSenderResources() {
        double usedBandWidth = fromDevice.usedBandWidth + dataExchangeResource.usedBandWidthAddition;
        double usedCPU = fromDevice.usedCPU + dataExchangeResource.usedCPUAddition;
        double usedMemory = fromDevice.usedMemory + dataExchangeResource.usedMemoryAddition;
        double usedStorage = fromDevice.usedStorage + dataExchangeResource.usedStorageAddition;

        if (usedBandWidth < fromDevice.bandWidthCapacity) {
            if (usedCPU < fromDevice.CPUCapacity) {
                if (usedMemory < fromDevice.memoryCapacity) {
                    if (usedStorage < fromDevice.storageCapacity) {
                        fromDevice.usedBandWidth = usedBandWidth;
                        fromDevice.usedCPU = usedCPU;
                        fromDevice.usedMemory = usedMemory;
                        fromDevice.usedStorage = usedStorage;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean consumeReceiverResources() {
        double usedBandWidth = toDevice.usedBandWidth + dataExchangeResource.usedBandWidthAddition;
        double usedCPU = toDevice.usedCPU + dataExchangeResource.usedCPUAddition;
        double usedMemory = toDevice.usedMemory + dataExchangeResource.usedMemoryAddition;
        double usedStorage = toDevice.usedStorage + dataExchangeResource.usedStorageAddition;

        if (usedBandWidth < toDevice.bandWidthCapacity) {
            if (usedCPU < toDevice.CPUCapacity) {
                if (usedMemory < toDevice.memoryCapacity) {
                    if (usedStorage < toDevice.storageCapacity) {
                        toDevice.usedBandWidth = usedBandWidth;
                        toDevice.usedCPU = usedCPU;
                        toDevice.usedMemory = usedMemory;
                        toDevice.usedStorage = usedStorage;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void setupRetryReceiverResourceConsumption() {
//        System.out.println("retryTimer is made!!!");
        retryTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                boolean success = consumeReceiverResources();
                if (success == true) {
                    retryTimer.purge();
                    retryTimer.shutdownNow();
                    setupSuccessReleaseResourceTimer();
                    toDevice.receiveMessageFromChild(thisDataExchange);

                    toDevice.signalConsole.append("Sending from: ");
                    toDevice.signalConsole.append(fromDevice.name).append(" ");
                    toDevice.signalConsole.append("Sending to: ");
                    toDevice.signalConsole.append(toDevice.name).append(" ");
                    toDevice.signalConsole.append("Metric: ");
                    toDevice.signalConsole.append(metric.name).append(" ");
                    toDevice.signalConsole.append("Time: ");
                    toDevice.signalConsole.append(time).append(" ");
                    toDevice.signalConsole.append("Message: ");
                    toDevice.signalConsole.append(message);
                    toDevice.signalConsole.append(System.lineSeparator());
                }
            }
        },0,fromDevice.retrySendToParentInterval,TimeUnit.MILLISECONDS);
    }

    public void setupTimeoutReleaseResourceTimer() {
//        System.out.println("timeout realeaseTimer is made!!!");
        realeaseTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                retryTimer.purge();
                retryTimer.shutdownNow();

                fromDevice.usedBandWidth = fromDevice.usedBandWidth - dataExchangeResource.usedBandWidthAddition;
                fromDevice.usedCPU = fromDevice.usedCPU - dataExchangeResource.usedCPUAddition;
                fromDevice.usedMemory = fromDevice.usedMemory - dataExchangeResource.usedMemoryAddition;
                fromDevice.usedStorage = fromDevice.usedStorage - dataExchangeResource.usedStorageAddition;

                fromDevice.removeSendingDataExchange(thisDataExchange);
                realeaseTimer.purge();
                realeaseTimer.shutdownNow();
            }
        },0,fromDevice.timeout,TimeUnit.MILLISECONDS);
    }

    public void setupSuccessReleaseResourceTimer() {
//        System.out.println("success realeaseTimer is made!!!");
        realeaseTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                fromDevice.usedBandWidth = fromDevice.usedBandWidth - dataExchangeResource.usedBandWidthAddition;
                fromDevice.usedCPU = fromDevice.usedCPU - dataExchangeResource.usedCPUAddition;
                fromDevice.usedMemory = fromDevice.usedMemory - dataExchangeResource.usedMemoryAddition;
                fromDevice.usedStorage = fromDevice.usedStorage - dataExchangeResource.usedStorageAddition;

                toDevice.usedBandWidth = toDevice.usedBandWidth - dataExchangeResource.usedBandWidthAddition;
                toDevice.usedCPU = toDevice.usedCPU - dataExchangeResource.usedCPUAddition;
                toDevice.usedMemory = toDevice.usedMemory - dataExchangeResource.usedMemoryAddition;
                toDevice.usedStorage = toDevice.usedStorage - dataExchangeResource.usedStorageAddition;

                fromDevice.removeSendingDataExchange(thisDataExchange);
                toDevice.removeReceivingDataExchange(thisDataExchange);
                realeaseTimer.purge();
                realeaseTimer.shutdownNow();
                realeaseTimer=null;
//                System.out.println("success Timer died!!!");
            }
        },0,fromDevice.latency,TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Object o) {
        if(time>((DataExchange)o).time)
        {
            return 1;
        }else if(time<((DataExchange)o).time)
        {
            return -1;
        }else
        {
            return 0;
        }
    }
}
