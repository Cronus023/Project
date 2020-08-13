angular.module('myApp.application', [])
    .controller('ApplicationCtrl', function($scope, $routeParams,$window, officeService) {
        officeService.get_office_by_provider_login($routeParams["login"]).then(function(value){
            if(value.body.title){
                alert(value.body.title)
                $window.location.href = '#!/main'
            }
            $scope.offices = value.body
        })
    })