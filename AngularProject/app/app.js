'use strict';

// Declare app level module which depends on views, and core components
angular.module('myApp', [
  'ngRoute',
  'myApp.login',
  'myApp.register',
    'myApp.toolbar',
    'auth',
    'myApp.main',
    'AuthInterceptor'
]).
config(['$locationProvider', '$routeProvider', '$httpProvider', function($locationProvider, $routeProvider, $httpProvider) {
  $httpProvider.interceptors.push('AuthInterceptor');

  $routeProvider.when('/login', {
    templateUrl: 'components/loginPage/login.html',
    controller: 'LoginCtrl'
  })
  $routeProvider.when('/main', {
    templateUrl: 'components/mainPage/main.html',
    controller: 'MainCtrl'
  })
  $routeProvider.when('/register', {
    templateUrl: 'components/registerPage/register.html',
    controller: 'RegisterCtrl'
  })

  $routeProvider.otherwise({
    redirectTo: '/login'}
    );
}]);
