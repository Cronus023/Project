angular.module('myApp.trainings.reg', [])
    .controller('RegTrainingsCtrl', function($scope, $routeParams,trainingService, $window) {
        $scope.message = ''
         $scope.trainingId = $routeParams["id"]
         trainingService.get_workers($scope.trainingId).then(function(value){
             $scope.data = value
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
        $scope.register = function (){
            trainingService.register_workers($scope.trainingId, $scope.selected).then(function(value){
                $scope.message = value.title
            })
            if($scope.message === 'ok'){
                setTimeout(function(){
                    $window.location.reload()
                }, 2000)
            }
            else {
                setTimeout(function(){
                    $scope.message = ''
                }, 1000)
            }
        }
    })