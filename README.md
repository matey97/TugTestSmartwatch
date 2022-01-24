# TUG Test Smartwatch

This WearOS application allows the data collection of accelerometer and gyroscope samples from
a smartwatch and send it to the companion app, [TUG Test Smartphone](https://github.com/matey97/TugTestSmartphone) 
running in the paired smartphone. That application uses the collected data to automatically extract 
the results of the execution of the TUG test.

The application has to functioning modes. In both modes, the application collects data from the 
accelerometer and gyroscope of the device and sends it to the smartphone. 
The changing behaviour is what is done with that data in the smartphone:

- TUG: the data is processed and used in a machine learning model to estimate the current activity
  being performed by the user. This is the mode to be used to execute the TUG test and automatically get
  its results with the smartphone application.
- COLLECTION: the data is stored in a file to be used later for diverse purposes (e.g., analysis, processing, model training, etc...)

## Usage requirements

- A WearOS Smartwatch.
- An Android smartphone paired through Bluetooth and running the [TUG Test Smartphone](https://github.com/matey97/TugTestSmartphone) app.

## License

See [LICENSE](./LICENSE).