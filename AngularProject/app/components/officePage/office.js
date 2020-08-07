angular.module('myApp.office', ['ngRoute'])
    .controller('OfficeCtrl', function($scope, $http, $window) {
        $scope.message = ''
        $scope.create = function(officeForm){
            if(officeForm.$valid){

                const officeBody = {
                    name: $scope.name,
                    location: $scope.location,
                    contact_details: $scope.contact,
                }
                $http.post('http://localhost:8080/create',officeBody).
                then(function (response) {
                    if(response.status == 400){
                        $scope.message = response.data.title
                    }
                    else{
                        $window.location.href = '#!/main'
                        $window.location.reload()
                    }
                })
            }
        }
    });