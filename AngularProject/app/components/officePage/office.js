angular.module('myApp.office', ['ngRoute'])
    .controller('OfficeCtrl', function ($scope, $http, officeService, $window) {

        $scope.errorMessage = ''

        $scope.create = function (officeForm) {
            if (officeForm.$valid) {
                const officeBody = {
                    name: $scope.nameOfOffice,
                    location: $scope.location,
                    contact_details: $scope.contact,
                }
                officeService.createOffice(officeBody).then(function (value) {
                    $scope.errorMessage = 'Such office already exist!'
                })
            }
        }

    });