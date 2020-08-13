angular.module('myApp.workers.trainings', [])
    .controller('TrainingsWorkersCtrl', function($scope, workersService, $routeParams,$window) {
        workersService.view_trainings($routeParams["id"]).then(function(value){
            $scope.trainings = value
        })
        $scope.dateFormat = function(date){
            const dateFormat = new Date(date)
            let dateString = ""
            if(dateFormat.getMinutes() < 10){
                dateString = dateFormat.getDate()+ "-" + dateFormat.getMonth()+ "-" +dateFormat.getFullYear() + ", " + dateFormat.getHours() + ":" + "0"+dateFormat.getMinutes()
            }
            else dateString = dateFormat.getDate()+ "-" + dateFormat.getMonth()+ "-" +dateFormat.getFullYear() + ", " + dateFormat.getHours() + ":" +  dateFormat.getMinutes()
            return dateString
        }
    })