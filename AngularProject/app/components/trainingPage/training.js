angular.module('myApp.trainings', [])
    .controller('TrainingsCtrl', function($scope, trainingService,$window) {
        $scope.userLogin = localStorage.getItem('UserLogin')
        trainingService.get_trainings().then(function(value){
            $scope.trainings = value
        })
        $scope.register = function(id){
            $window.location.href = `#!/trainings/registration/${id}`
        }
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