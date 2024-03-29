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

    'myApp.guards.auth',
    'myApp.guards.provider',
    'myApp.guards.training',
    'myApp.guards.application',

    'myApp.workers.addWorker',
    'myApp.workers.edit',
    'myApp.workers.trainings',

    'myApp.trainings',
    'myApp.changeRole',
    'myApp.trainings.add',
    'myApp.trainings.reg',
    'myApp.trainings.control',
    'myApp.trainings.control.workers',
    'myApp.trainings.visit',

    'myApp.application',
    'myApp.application.my',
    'myApp.application.form',
    'myApp.application.reviewers',
    'myApp.application.history',
    'myApp.application.finalDecision',
    'myApp.application.reviewers.curriculum',
    'myApp.application.reviewers.regular',

]).config(['$locationProvider', '$routeProvider', '$httpProvider', function ($locationProvider, $routeProvider, $httpProvider) {
    $httpProvider.interceptors.push('AuthInterceptor');

    $routeProvider.when('/login', {
        templateUrl: 'components/loginPage/login.html',
        controller: 'LoginCtrl',
        resolve: {
            check: function ($window, authGuard) {
                authGuard.checkRedirect()
            }
        },
    })

    $routeProvider.when('/main', {
        templateUrl: 'components/mainPage/main.html',
        controller: 'MainCtrl',
        resolve: {
            check: function ($window, authGuard, providerGuard) {
                authGuard.checkLogin()
                providerGuard.checkUserRole()
            }
        }
    })

    $routeProvider.when('/changeRole', {
        templateUrl: 'components/changeRolePage/changeRole.html',
        controller: 'ChangeRoleCtrl',
        resolve: {
            check: function ($window, authGuard) {
                authGuard.checkLogin()
            }
        }
    })

    $routeProvider.when('/register', {
        templateUrl: 'components/registerPage/register.html',
        controller: 'RegisterCtrl',
        resolve: {
            check: function ($window, authGuard) {
                authGuard.checkRedirect()
            }
        },
    })

    $routeProvider.when('/office', {
        templateUrl: 'components/officePage/office.html',
        controller: 'OfficeCtrl',
        resolve: {
            check: function ($window, authGuard, providerGuard) {
                authGuard.checkLogin()
                providerGuard.checkUserRole()
            }
        }
    })

    $routeProvider.when(`/workers/:officeName`, {
        templateUrl: 'components/workersPage/workers.html',
        controller: 'WorkersCtrl',
        resolve: {
            check: function ($window, authGuard, providerGuard) {
                authGuard.checkLogin()
                providerGuard.checkWorkersPage()
            }
        }
    })

    $routeProvider.when(`/workers/:officeName/trainings/:workerID`, {
        templateUrl: 'components/workersPage/workerTrainingsPage/workerTrainings.html',
        controller: 'TrainingsWorkersCtrl',
        resolve: {
            check: function ($window, authGuard, providerGuard) {
                authGuard.checkLogin()
                providerGuard.checkWorkersTrainingsAndEditPages()
            }
        }
    })

    $routeProvider.when(`/workers/add/:officeName`, {
        templateUrl: 'components/workersPage/addWorkerPage/addWorker.html',
        controller: 'AddWorkerCtrl',
        resolve: {
            check: function ($window, authGuard, providerGuard) {
                authGuard.checkLogin()
                providerGuard.checkWorkersPage()
            }
        }
    })

    $routeProvider.when(`/workers/edit/:officeName/:workerID`, {
        templateUrl: 'components/workersPage/editWorkerPage/editWorker.html',
        controller: 'EditWorkersCtrl',
        resolve: {
            check: function ($window, authGuard, providerGuard) {
                authGuard.checkLogin()
                providerGuard.checkWorkersTrainingsAndEditPages()
            }
        }
    })

    $routeProvider.when(`/trainings`, {
        templateUrl: 'components/trainingPage/training.html',
        controller: 'TrainingsCtrl',
        resolve: {
            check: function ($window, authGuard, trainingGuard) {
                authGuard.checkLogin()
                trainingGuard.checkUserRole()
            }
        }
    })

    $routeProvider.when(`/trainings/control/:trainingID`, {
        templateUrl: 'components/trainingPage/controlTrainingPage/controlTraining.html',
        controller: 'ControlTrainingsCtrl',
        resolve: {
            check: function ($window, authGuard, trainingGuard) {
                authGuard.checkLogin()
                trainingGuard.checkTrainingControlPage();
            }
        }
    })

    $routeProvider.when(`/trainings/control/workers/:trainingID`, {
        templateUrl: 'components/trainingPage/controlTrainingPage/workersControlPage/workersControl.html',
        controller: 'WorkersControlTrainingsCtrl',
        resolve: {
            check: function ($window, authGuard, trainingGuard) {
                authGuard.checkLogin()
                trainingGuard.checkTrainingControlPage();
            }
        }
    })

    $routeProvider.when(`/trainings/add/:userLogin`, {
        templateUrl: 'components/trainingPage/addTrainingPage/addTraining.html',
        controller: 'AddTrainingsCtrl',
        resolve: {
            check: function ($window, authGuard, trainingGuard) {
                authGuard.checkLogin()
                trainingGuard.checkAddTrainingPage()
            }
        }
    })

    $routeProvider.when(`/trainings/visit/:trainingID`, {
        templateUrl: 'components/trainingPage/visitTrainingPage/visitTraining.html',
        controller: 'VisitTrainingsCtrl',
        resolve: {
            check: function ($window, authGuard, trainingGuard) {
                authGuard.checkLogin()
                trainingGuard.checkTrainingControlPage();
            }
        }
    })

    $routeProvider.when(`/trainings/registration/:trainingID`, {
        templateUrl: 'components/trainingPage/regTrainingPage/regTraining.html',
        controller: 'RegTrainingsCtrl',
        resolve: {
            check: function ($window, authGuard, trainingGuard) {
                authGuard.checkLogin()
                trainingGuard.checkTrainingControlPage();
            }
        }
    })

    $routeProvider.when(`/application/my/:userLogin`, {
        templateUrl: 'components/applicationPage/myApplicationsPage/myApplications.html',
        controller: 'MyApplicationCtrl',
        resolve: {
            check: function ($window, authGuard, providerGuard) {
                authGuard.checkLogin()
                providerGuard.checkMyApplicationsPage()
            }
        }
    })


    $routeProvider.when(`/application/:userLogin`, {
        templateUrl: 'components/applicationPage/application.html',
        controller: 'ApplicationCtrl',
        resolve: {
            check: function ($window, authGuard, providerGuard) {
                authGuard.checkLogin()
                providerGuard.checkApplicationsPage();
            }
        }
    })

    $routeProvider.when(`/application/reviewers/view`, {
        templateUrl: 'components/applicationPage/reviewersPage/reviewers.html',
        controller: 'ReviewersApplicationCtrl',
        resolve: {
            check: function ($window, authGuard, applicationGuard) {
                authGuard.checkLogin()
                applicationGuard.checkUserRole();
            }
        }
    })

    $routeProvider.when(`/application/history/:applicationID`, {
        templateUrl: 'components/applicationPage/reviewersPage/historyOfApplicationPage/history.html',
        controller: 'HistoryApplicationCtrl',
        resolve: {
            check: function ($window, authGuard, applicationGuard) {
                authGuard.checkLogin()
                applicationGuard.checkApplicationHistoryPage()
            }
        }
    })

    $routeProvider.when(`/application/decision/:applicationID`, {
        templateUrl: 'components/applicationPage/reviewersPage/finalDicisionPage/finalDecision.html',
        controller: 'FinalDecisionApplicationCtrl',
        resolve: {
            check: function ($window, authGuard, applicationGuard) {
                authGuard.checkLogin()
                applicationGuard.checkFinalDecisionPage()
            }
        }
    })

    $routeProvider.when(`/application/reviewers/curriculum/:applicationID`, {
        templateUrl: 'components/applicationPage/reviewersPage/curriculumPage/curriculum.html',
        controller: 'CurriculumReviewersApplicationCtrl',
        resolve: {
            check: function ($window, authGuard, applicationGuard) {
                authGuard.checkLogin()
                applicationGuard.check_CURRICULUM_REVIEWER_Page()
            }
        }
    })

    $routeProvider.when(`/application/reviewers/regular/:applicationID`, {
        templateUrl: 'components/applicationPage/reviewersPage/regularPage/regular.html',
        controller: 'RegularReviewersApplicationCtrl',
        resolve: {
            check: function ($window, authGuard, applicationGuard) {
                authGuard.checkLogin()
                applicationGuard.check_REGULAR_REVIEWER_Page()
            }
        }
    })

    $routeProvider.when(`/application/form/:officeName`, {
        templateUrl: 'components/applicationPage/formOfApplicationPage/formOfApplication.html',
        controller: 'FormApplicationCtrl',
        resolve: {
            check: function ($window, authGuard, providerGuard) {
                authGuard.checkLogin()
                providerGuard.checkFormApplicationPage()
            }
        }
    })

    $routeProvider.otherwise({
            redirectTo: '/login'
        }
    )
}])
