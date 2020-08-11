angular.module('myApp.trainings', [])
    .controller('TrainingsCtrl', function($scope, trainingService,$window) {
        $scope.userLogin = localStorage.getItem('UserLogin')
        trainingService.get_trainings().then(function(value){
            $scope.trainings = value
            console.log(value)
        })
        $scope.check = function(login){
            let flag = false
            console.log($scope.userLogin)
            if($scope.userLogin == login){
                return true
            }
            return flag
        }
    })