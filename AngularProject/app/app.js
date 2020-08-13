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
  'workers',
  'training',

  'myApp.workers.addWorker',
  'myApp.workers.edit',
  'myApp.workers.trainings',

  'myApp.trainings',
  'myApp.trainings.add',
  'myApp.trainings.reg',
  'myApp.trainings.control',
  'myApp.trainings.visit',
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
  $routeProvider.when(`/workers/:id`, {
    templateUrl: 'components/workersPage/workers.html',
    controller: 'WorkersCtrl',
  })
  $routeProvider.when(`/workers/trainings/:id`, {
    templateUrl: 'components/workersPage/workerTrainingsPage/workerTrainings.html',
    controller: 'TrainingsWorkersCtrl',
  })
  $routeProvider.when(`/workers/add/:id`, {
    templateUrl: 'components/workersPage/addWorkerPage/addWorker.html',
    controller: 'AddWorkerCtrl',
  })
  $routeProvider.when(`/trainings`, {
    templateUrl: 'components/trainingPage/training.html',
    controller: 'TrainingsCtrl',
  })
  $routeProvider.when(`/trainings/control/:id`, {
    templateUrl: 'components/trainingPage/controlTrainingPage/controlTraining.html',
    controller: 'ControlTrainingsCtrl',
  })
  $routeProvider.when(`/trainings/add/:name`, {
    templateUrl: 'components/trainingPage/addTrainingPage/addTraining.html',
    controller: 'AddTrainingsCtrl',
  })
  $routeProvider.when(`/trainings/visit/:id`, {
    templateUrl: 'components/trainingPage/visitTrainingPage/visitTraining.html',
    controller: 'VisitTrainingsCtrl',
  })
  $routeProvider.when(`/trainings/registration/:id`, {
    templateUrl: 'components/trainingPage/regTrainingPage/regTraining.html',
    controller: 'RegTrainingsCtrl',
  })
  $routeProvider.when(`/workers/edit/:name/:id`, {
    templateUrl: 'components/workersPage/editWorkerPage/editWorker.html',
    controller: 'EditWorkersCtrl',
  })
  $routeProvider.otherwise({
    redirectTo: '/login'}
    )
}])
