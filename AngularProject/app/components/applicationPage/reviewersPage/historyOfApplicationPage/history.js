angular.module('myApp.application.history', [])
    .controller('HistoryApplicationCtrl', function($scope, $routeParams,$window, applicationService, authService) {
        applicationService.get_history_of_application($routeParams["applicationID"]).then(function(value){
            $scope.data = value
        })
        $scope.dateFormat = function(date){
            return authService.dateFormat(date)
        }
        $scope.back = function(){
            $window.location.href = '#!/application/reviewers/view'
        }
    })