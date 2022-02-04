import { NgxLoggerLevel } from 'ngx-logger';

export const environment = {
  production: true,
  logLevel: NgxLoggerLevel.OFF,
  serverLogLevel: NgxLoggerLevel.ERROR,
  backendBaseUrl: 'http://ec2-3-248-217-191.eu-west-1.compute.amazonaws.com:8080'
};
