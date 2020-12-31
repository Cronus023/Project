angular.module('myApp.changeRole', [])
    .controller('ChangeRoleCtrl', function ($scope, $window, authService) {

        $scope.userLogin = localStorage.getItem('UserLogin')

        authService.getRoles($scope.userLogin).then(function (value) {
            if (value.title) {
                alert(value.title)
                $window.location.href = '#!/login'
                $window.location.reload()
            }
            $scope.roles = value
        })

        $scope.chooseRole = function (role) {
            localStorage.setItem('UserRole', role)
            authService.userNavigation(role)
        }

    })