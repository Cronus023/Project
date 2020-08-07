angular.module('myApp.workers', [])
    .controller('WorkersCtrl', function($scope, authService) {
        $scope.checkAll = false
        $scope.data = [
            {
                name: "lol", surname: "kek"
            },
            {
                name: "lol1", surname: "kek2"
            }
        ]
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
    })