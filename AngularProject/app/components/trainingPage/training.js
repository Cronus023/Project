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
        $scope.visit = function(id){
            $window.location.href = `#!/trainings/visit/${id}`
        }
        $scope.control = function(id){
            $window.location.href = `#!/trainings/control/${id}`
        }
        $scope.checkControl = function(training){
            const date = new Date()
            const dateOfEnd = new Date(training.date )
            let flag = false
            if(training.trainerID.login === $scope.userLogin && dateOfEnd >= date){
                flag = true
            }
            return flag
        }

        $scope.checkRegister = function(training){
            const date = new Date()
            const dateOfEnd = new Date(training.dateOfEnd)
            let flag = false
            if($scope.userLogin && dateOfEnd >= date){
                flag = true
            }
            return flag
        }
    })