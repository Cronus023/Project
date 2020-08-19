angular.module('myApp.login', ['ngRoute'])
    .controller('LoginCtrl', function($scope, authService, $window) {
        $scope.auth = function(loginForm)
        {
            if(loginForm.$valid){
                const authBody = {
                    login: $scope.login,
                    password: $scope.password
                }
                authService.login(authBody).then(function(value){
                    if(value.title){
                        $scope.message = value.title
                    }
                    else{
                        localStorage.setItem('UserRoles', value.roles)
                        localStorage.setItem('UserRole', value.roles[0])
                        localStorage.setItem('JwtToken', value.token)
                        localStorage.setItem('UserLogin', authBody.login)
                        if(value.roles[0] === 'PROVIDER'){
                            $window.location.href = '#!/main'
                            $window.location.reload()
                        }
                        if(value.roles[0] === 'CURRICULUM_REVIEWER' || value.roles[0] === 'REGULAR_REVIEWER' ||  value.roles[0] === 'SUPERVISION' ){
                            $window.location.href = '#!/application/reviewers/view'
                            $window.location.reload()
                        }
                        if(value.roles[0] === 'TRAINING_OPERATOR'){
                            $window.location.href = '#!/trainings'
                            $window.location.reload()
                        }
                    }
                })
            }
        }
    })