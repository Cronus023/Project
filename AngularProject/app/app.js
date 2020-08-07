'use strict';

// Declare app level module which depends on views, and core components
angular.module('myApp', [
  'ngRoute',
  'myApp.login',
  'myApp.register',
  'myApp.toolbar',
  'auth',
  'myApp.main',
  'AuthInterceptor',
  'myApp.office',
  'office',
  'myApp.workers',
]).
config(['$locationProvider', '$routeProvider', '$httpProvider', function($locationProvider, $routeProvider, $httpProvider, authService) {
  $httpProvider.interceptors.push('AuthInterceptor');
  $routeProvider.when('/login', {
    templateUrl: 'components/loginPage/login.html',
    controller: 'LoginCtrl',
  })
  $routeProvider.when('/main', {
    templateUrl: 'components/mainPage/main.html',
    controller: 'MainCtrl',
    resolve:{
      check:function($window){
        if(localStorage.getItem('JwtToken') == null){
          $window.location.href = '#!/login'
        }
      }
    }

  })
  $routeProvider.when('/register', {
    templateUrl: 'components/registerPage/register.html',
    controller: 'RegisterCtrl'
  })
  $routeProvider.when('/office', {
    templateUrl: 'components/officePage/office.html',
    controller: 'OfficeCtrl'
  })
  $routeProvider.when(`/workers/`, {
    templateUrl: 'components/workersPage/workers.html',
    controller: 'WorkersCtrl'
  })
  $routeProvider.otherwise({
    redirectTo: '/login'}
    )
}])
