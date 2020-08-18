'use strict';

angular.module('myApp.toolbar', [])
    .directive('toolbar', function(){
        return{
            templateUrl: 'components/toolbar/toolbar.html',
            controller: 'toolbarCtrl'
        }
    })
    .controller('toolbarCtrl', function($scope, authService, $window){
        $scope.token = localStorage.getItem('JwtToken')
        $scope.role = localStorage.getItem('UserRole')
        $scope.name = localStorage.getItem('UserLogin')

        $scope.logout = function(){
            authService.logout()
        }
        $scope.addTraining = function(){
            $window.location.href = `#!/trainings/add/${$scope.name}`
        }
        $scope.application = function(){
            const login = localStorage.getItem('UserLogin')
            $window.location.href = `#!/application/${login}`
        }
        $scope.myApplications = function(){
            const login = localStorage.getItem('UserLogin')
            $window.location.href = `#!/application/my/${login}`
        }
    })
