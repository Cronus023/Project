angular.module('myApp.office', ['ngRoute'])
    .controller('OfficeCtrl', function($scope, $http, $window) {
        $scope.message = ''
        $scope.create = function(officeForm){
            if(officeForm.$valid){

                const officeBody = {
                    name: $scope.name,
                    location: $scope.location,
                    photo: $scope.photo,
                    contact_details: $scope.contact,
                }
                $http.post('http://localhost:8080/create',officeBody).
                then(function (response) {
                    if(response.status == 400){
                        console.log(response)
                        $scope.message = response.data.title
                    }
                    else{
                        $window.location.href = '#!/main'
                    }
                })
            }
        }
    });