angular.module('myApp.trainings', [])
    .controller('TrainingsCtrl', function($scope, trainingService, $routeParams,$window) {
        trainingService.get_trainings().then(function(value){
            $scope.trainings = value
            console.log(value)
        })
    })