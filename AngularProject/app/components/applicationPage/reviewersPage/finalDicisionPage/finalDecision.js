angular.module('myApp.application.finalDecision', [])
    .controller('FinalDecisionApplicationCtrl', function ($scope, $routeParams, $window, applicationService) {

        $scope.finalDecision = function (decision) {
            applicationService.finalDecision($routeParams["applicationID"], decision).then(function (value) {
                if (value.title !== 'ok!') {
                    alert(value.title)
                    $window.location.href = '#!/application/reviewers/view'
                }
                $window.location.href = '#!/application/reviewers/view'
            })
        }

    })