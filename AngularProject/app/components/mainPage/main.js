'use strict';

angular.module('myApp.main', ['ngRoute'])
    .controller('MainCtrl', function($scope, $http) {
        $http.get('http://localhost:8080/get_office').
        then(function success(response) {
            console.log(response.data)
            $scope.offices = response.data
        })
    });