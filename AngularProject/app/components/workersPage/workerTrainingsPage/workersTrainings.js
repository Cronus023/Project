angular.module('myApp.workers.trainings', [])
    .controller('TrainingsWorkersCtrl', function($scope, workersService, $routeParams,authService) {
        workersService.view_trainings($routeParams["workerID"]).then(function(value){
            $scope.trainings = value
            console.log(value)
        })
        $scope.dateFormat = function(date){
            return authService.dateFormat(date)
        }
    })