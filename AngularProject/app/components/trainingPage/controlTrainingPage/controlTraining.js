angular.module('myApp.trainings.control', [])
    .controller('ControlTrainingsCtrl', function($scope, trainingService,$routeParams, $window) {
        $scope.message = ''
        trainingService.get_training_by_id($routeParams["id"]).then(function(value){
            $scope.data = value
            $scope.data.date = new Date(value.date)
            $scope.data.dateOfEnd = new Date(value.dateOfEnd)
        })
        $scope.typesOfTraining = [
            { key: 1, value: "PSYCHOLOGICAL"},
            { key: 2, value: "PHYSICAL" },
            { key: 3, value: "PERSONAL_GROWTH"},
            { key: 4, value: "COMMUNICATION" },
        ]
        $scope.deleteTraining = function(){
            trainingService.delete_training($routeParams["id"]).then(function(value){
                if(value.title == 'ok!'){
                    $window.location.href = '#!/trainings'
                }
                else{
                    $scope.message = value.title
                }
            })
        }
        $scope.editTraining = function(trainingForm){
            if(trainingForm.$valid){
                const date = new Date()
                if($scope.data.date <=  date || $scope.data.dateOfEnd <=  date){
                    $scope.message = "Incorrect date"
                }
                else if($scope.data.dateOfEnd > $scope.data.date){
                    $scope.message = "The training date must be later than the registration date "
                }
                else {
                    trainingService.edit_training($scope.data).then(function(value){
                        $scope.message = value.title
                    })
                    setTimeout(function(){
                        $scope.message = ''
                    }, 1000)
                }
            }
        }
        $scope.workersControl =function(){
            $window.location.href = `#!/trainings/control/workers/${$routeParams["id"]}`
        }
    })


