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
  'application',

  'myApp.workers.addWorker',
  'myApp.workers.edit',
  'myApp.workers.trainings',

  'myApp.trainings',
  'myApp.trainings.add',
  'myApp.trainings.reg',
  'myApp.trainings.control',
  'myApp.trainings.control.workers',
  'myApp.trainings.visit',

  'myApp.application',
  'myApp.application.form',
  'myApp.application.reviewers',
  'myApp.application.history',
  'myApp.application.reviewers.curriculum',
  'myApp.application.reviewers.regular',

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
      check:function($window, authService){
        authService.checkLogin()
      }
    }

  })
  $routeProvider.when('/register', {
    templateUrl: 'components/registerPage/register.html',
    controller: 'RegisterCtrl',

  })
  $routeProvider.when('/office', {
    templateUrl: 'components/officePage/office.html',
    controller: 'OfficeCtrl',
    resolve:{
      check:function($window, authService){
        authService.checkLogin()
      }
    }
  })
  $routeProvider.when(`/workers/:id`, {
    templateUrl: 'components/workersPage/workers.html',
    controller: 'WorkersCtrl',
    resolve:{
      check:function($window, authService){
        authService.checkLogin()
      }
    }
  })
  $routeProvider.when(`/workers/trainings/:id`, {
    templateUrl: 'components/workersPage/workerTrainingsPage/workerTrainings.html',
    controller: 'TrainingsWorkersCtrl',
    resolve:{
      check:function($window, authService){
        authService.checkLogin()
      }
    }
  })
  $routeProvider.when(`/workers/add/:id`, {
    templateUrl: 'components/workersPage/addWorkerPage/addWorker.html',
    controller: 'AddWorkerCtrl',
    resolve:{
      check:function($window, authService){
        authService.checkLogin()
      }
    }
  })
  $routeProvider.when(`/trainings`, {
    templateUrl: 'components/trainingPage/training.html',
    controller: 'TrainingsCtrl',
    resolve:{
      check:function($window, authService){
        authService.checkLogin()
      }
    }
  })
  $routeProvider.when(`/trainings/control/:id`, {
    templateUrl: 'components/trainingPage/controlTrainingPage/controlTraining.html',
    controller: 'ControlTrainingsCtrl',
    resolve:{
      check:function($window, authService){
        authService.checkLogin()
      }
    }
  })
  $routeProvider.when(`/trainings/control/workers/:id`, {
    templateUrl: 'components/trainingPage/controlTrainingPage/workersControlPage/workersControl.html',
    controller: 'WorkersControlTrainingsCtrl',
    resolve:{
      check:function($window, authService){
        authService.checkLogin()
      }
    }
  })
  $routeProvider.when(`/trainings/add/:name`, {
    templateUrl: 'components/trainingPage/addTrainingPage/addTraining.html',
    controller: 'AddTrainingsCtrl',
    resolve:{
      check:function($window, authService){
        authService.checkLogin()
      }
    }

  })
  $routeProvider.when(`/trainings/visit/:id`, {
    templateUrl: 'components/trainingPage/visitTrainingPage/visitTraining.html',
    controller: 'VisitTrainingsCtrl',
    resolve:{
      check:function($window, authService){
        authService.checkLogin()
      }
    }
  })
  $routeProvider.when(`/trainings/registration/:id`, {
    templateUrl: 'components/trainingPage/regTrainingPage/regTraining.html',
    controller: 'RegTrainingsCtrl',
    resolve:{
      check:function($window, authService){
        authService.checkLogin()
      }
    }
  })
  $routeProvider.when(`/workers/edit/:name/:id`, {
    templateUrl: 'components/workersPage/editWorkerPage/editWorker.html',
    controller: 'EditWorkersCtrl',
    resolve:{
      check:function($window, authService){
        authService.checkLogin()
      }
    }
  })
  $routeProvider.when(`/application/:login`, {
    templateUrl: 'components/applicationPage/application.html',
    controller: 'ApplicationCtrl',
    resolve:{
      check:function($window, authService){
        authService.checkLogin()
      }
    }
  })
  $routeProvider.when(`/application/reviewers/view`, {
    templateUrl: 'components/applicationPage/reviewersPage/reviewers.html',
    controller: 'ReviewersApplicationCtrl',
    resolve:{
      check:function($window, authService){
        authService.checkLogin()
      }
    }
  })
  $routeProvider.when(`/application/history/:id`, {
    templateUrl: 'components/applicationPage/reviewersPage/historyOfApplicationPage/history.html',
    controller: 'HistoryApplicationCtrl',
    resolve:{
      check:function($window, authService){
        authService.checkLogin()
      }
    }
  })
  $routeProvider.when(`/application/reviewers/curriculum/:id`, {
    templateUrl: 'components/applicationPage/reviewersPage/curriculumPage/curriculum.html',
    controller: 'CurriculumReviewersApplicationCtrl',
    resolve:{
      check:function($window, authService){
        authService.checkLogin()
      }
    }
  })
  $routeProvider.when(`/application/reviewers/regular/:id`, {
    templateUrl: 'components/applicationPage/reviewersPage/regularPage/regular.html',
    controller: 'RegularReviewersApplicationCtrl',
    resolve:{
      check:function($window, authService){
        authService.checkLogin()
      }
    }
  })
  $routeProvider.when(`/application/form/:name`, {
    templateUrl: 'components/applicationPage/formOfApplicationPage/formOfApplication.html',
    controller: 'FormApplicationCtrl',
    resolve:{
      check:function($window, authService){
        authService.checkLogin()
      }
    }
  })

  $routeProvider.otherwise({
    redirectTo: '/login'}
    )
}])
