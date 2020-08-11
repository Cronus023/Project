angular.module('myApp.trainings', [])
    .controller('TrainingsCtrl', function($scope, trainingService, $routeParams,$window) {
        trainingService.get_trainings()
    })