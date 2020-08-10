angular.module('myApp.workers', [])
    .controller('WorkersCtrl', function($scope, workersService, $routeParams,$window) {
        $scope.checkAll = false
        
        $scope.id = $routeParams["id"]
        workersService.get_workers($scope.id).then(function(value){
            $scope.data = value
            console.log(value)
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

        $scope.click = function(simple){
            console.log(simple)
        }

        $scope.add = function(){
            $window.location.href = `#!/workers/add/${$scope.id}`
        }
        $scope.delete = function(){
            console.log($scope.selected)
            workersService.delete()
        }
        $scope.edit = function(){
            workersService.edit()
            console.log($scope.id)
        }
    })