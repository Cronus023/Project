angular.module('myApp.application', [])
    .controller('ApplicationCtrl', function ($scope, $routeParams, $window, officeService) {

        officeService.getOfficesByLogin($routeParams["userLogin"]).then(function (value) {
            if (value.title) {
                alert(value.title)
                $window.location.href = '#!/main'
            }
            $scope.offices = value
        })

        $scope.makeApplication = function (officeName) {
            $window.location.href = `#!/application/form/${officeName}`
        }

    })