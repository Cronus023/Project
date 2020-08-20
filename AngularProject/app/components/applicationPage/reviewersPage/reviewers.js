angular.module('myApp.application.reviewers', [])
    .controller('ReviewersApplicationCtrl', function($scope, $routeParams,$window, applicationService) {
        $scope.userRole = localStorage.getItem('UserRole')
        applicationService.get_applications().then(function(value){
            $scope.data = value
        })

        $scope.curriculum = function(id){
            $window.location.href= `#!/application/reviewers/curriculum/${id}`
        }
        $scope.regular = function(id){
            $window.location.href= `#!/application/reviewers/regular/${id}`
        }
        $scope.historyOfApplication = function(id){
            $window.location.href= `#!/application/history/${id}`
        }
        $scope.finalDecision = function(id){
            $window.location.href= `#!/application/decision/${id}`

        }
    })