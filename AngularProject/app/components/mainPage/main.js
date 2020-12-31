angular.module('myApp.main', ['ngRoute'])
    .controller('MainCtrl', function ($scope, officeService, $window, authService) {

        officeService.getOffices().then(function (value) {
            $scope.offices = value
        })

        $scope.becomeProvider = function (office) {
            officeService.becomeProvider(office)
        }

        $scope.checkProvider = function (office) {
            let flag = false

            if (office.leaderID.length === 0) return false
            else office.leaderID.map(function(item) {
                    if (item.login === localStorage.getItem('UserLogin'))
                        flag = true
                })
            return flag
        }

        $scope.viewWorkers = function (office) {
            $window.location.href = `/#!/workers/${office.name}`
        }

        $scope.dateFormat = function (date) {
            return authService.dateFormat(date)
        }

    })