angular.module('myApp.trainings.control.workers', [])
    .controller('WorkersControlTrainingsCtrl', function($scope, trainingService,$routeParams, $window) {
        $scope.checkAll = false
        $scope.message = ''
        $scope.id = $routeParams["trainingID"]
        trainingService.get_training_by_id($scope.id).then(function(value){
            $scope.training = value
            $scope.data = value.workerID
        })

        $scope.selected = []
        $scope.exist = function(item){
            return $scope.selected.indexOf(item) > -1
        }
        $scope.select = function(item){
            const index = $scope.selected.indexOf(item)
            if(index > -1){
                $scope.selected.splice(index, 1)
            }
            else $scope.selected.push(item)
        }
        $scope.selectAll = function(){
            if(!$scope.checkAll){
                $scope.data.map(function(item){
                    const index = $scope.selected.indexOf(item)
                    if(index >=0){
                        return true
                    }
                    else{
                        $scope.selected.push(item)
                    }
                })
            }
            else{
                $scope.selected = []
            }
        }
        $scope.delete = function(){
            trainingService.delete_workers_in_training($scope.id,$scope.selected).then(function(value){
                $scope.message = value.title
                if($scope.message === 'ok!'){
                    setTimeout(function(){
                        $window.location.reload()
                    }, 1000)
                }
                else{
                    setTimeout(function(){
                        $scope.message = ''
                    }, 1000)
                }
                console.log(value)
            })
        }
    })


