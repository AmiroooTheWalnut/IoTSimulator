This repository is an IoT simulator.
Dependancies are:
https://github.com/AmiroooTheWalnut/FastCSV
https://github.com/AmiroooTheWalnut/WekaForecasterAMES
https://github.com/AmiroooTheWalnut/WekaRevisedForIoTProject
And the testing environment is:
https://github.com/AmiroooTheWalnut/WekaForecastingTests


Limitations:
-Frequency difference between metrics should not exceed the size of "interpolationBuffer" variable. For instance, if 
we have metric one signaling every 1 second and metric two every hour, then if size of "interpolationBuffer" be 10,
we have 10 seconds signals for metric one and 10 hours for metric two. In this case the overlap between two metrics is
either zero (10 second signals are between two hourly signals) or one. Therefore, no correlation can be calculated.
One solution to this is to increase the size of "interpolationBuffer" to have at least 2 overlaps which is not enough
for calculating correlation but the minimum value to calculate the value.
-Although simulation time can go until "9,223,372,036,854,775,808" (long) but interpolation connection to Matlab uses double.
"Double" can go to 1.79769E+308 but it's not accurate. This may introduce minor error.
-The messages (signal floating number) are stored in string. On calculations it is converted to double in runtime.
This will degrade the performance.

