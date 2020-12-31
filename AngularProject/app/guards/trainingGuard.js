const trainingGuard = angular.module('myApp.guards.training', [])
trainingGuard.factory('trainingGuard', ['$http', '$q', '$window', '$route', 'authService', function ($http, $q, $window, $route, authService) {
    return {
        checkUserRole: function () {
            const role = localStorage.getItem('UserRole')
            if (role !== 'PROVIDER' && role !== 'TRAINING_OPERATOR') {
                alert('Your current role is ' + role + ',' + 'change it to PROVIDER or TRAINING_OPERATOR before entering this page')
                $window.location.href = '#!/changeRole'
                $window.location.reload()
            }
        },
        in_DepthCheckRole: function () {
            const role = localStorage.getItem('UserRole')
            if (role !== 'TRAINING_OPERATOR') {
                alert('Your current role is ' + role + ',' + 'change it to TRAINING_OPERATOR before entering this page')
                $window.location.href = '#!/changeRole'
                $window.location.reload()
            }
        },

        checkTrainingControlPage: function () {
            this.in_DepthCheckRole()
            const role = localStorage.getItem('UserRole')
            if (role === 'TRAINING_OPERATOR') {
                const login = localStorage.getItem('UserLogin')
                $http.get(`http://localhost:8080/guards/checkTrainingControl?login=${login}&id=${$route.current.params.trainingID}`).then(function (response) {
                    const data = response.data
                    if (data.title !== 'ok!') {
                        if (data.title === 'bad!') {
                            alert('You can not control this training!')
                            $window.location.href = '#!/trainings'
                            $window.location.reload()
                        }
                        if (response.status === 400) {
                            if (data.title === 'badStatus1') {
                                alert("Such training does not exist")
                                $window.location.href = '#!/trainings'
                                $window.location.reload()
                            }
                            if (data.title === 'badStatus2') {
                                alert("Wrong user login!")
                                localStorage.setItem('JwtToken', null)
                                localStorage.setItem('UserLogin', null)
                                localStorage.setItem('UserRole', null)
                                $window.location.href = '#!/login'
                            }
                        }
                    }
                })
            }

        },
        checkAddTrainingPage: function () {
            this.in_DepthCheckRole()
            const role = localStorage.getItem('UserRole')
            if (role === 'TRAINING_OPERATOR') {
                const login = localStorage.getItem('UserLogin')
                if ($route.current.params.userLogin !== login) {
                    alert('You cannot add training on behalf of another training_operator!')
                    $window.location.href = '#!/trainings'
                    $window.location.reload()
                }
            }
        }
    }
}])