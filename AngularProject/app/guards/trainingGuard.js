const trainingGuard = angular.module('myApp.guards.training', [])
trainingGuard.factory('trainingGuard',['$http','$q','$window', '$route', function($http,  $q, $window, $route){
    return{
        checkUserRole: function(){
            const role = localStorage.getItem('UserRole')
            if(role !== 'PROVIDER' && role!== 'TRAINING_OPERATOR'){
                alert('Your current role is ' +  role + ',' + 'change it to PROVIDER or TRAINING_OPERATOR before entering this page')
                $window.location.href = '#!/changeRole'
                $window.location.reload()
            }
        },
    }
}] )