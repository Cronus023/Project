angular.module('myApp.application.my', [])
    .controller('MyApplicationCtrl', function($scope, $routeParams,$window, applicationService) {
        applicationService.get_provider_applications().then(function(value){
            $scope.data = value
        })
        $scope.back = function(){
            $window.location.href = '#!/main'
        }
    })