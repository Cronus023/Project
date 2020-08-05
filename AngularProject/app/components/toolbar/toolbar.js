'use strict';

angular.module('myApp.toolbar', [])
    .directive('toolbar', function(){
        return{
            templateUrl: 'components/toolbar/toolbar.html',
            controller: 'toolbarCtrl'
        }
    })
    .controller('toolbarCtrl', function($scope, authService){
        $scope.token = localStorage.getItem('JwtToken')
        $scope.logout = function(){
            authService.logout()
        }
    })
