const providerGuard = angular.module('myApp.guards.provider', [])
providerGuard.factory('providerGuard',['$http','$q','$window', '$route', function($http,  $q, $window, $route){
    return{
        checkUserRole: function(){
            const role = localStorage.getItem('UserRole')
            if(role !== 'PROVIDER'){
                alert('Your current role is ' +  role + ',' + 'change it to PROVIDER before entering this page')
                $window.location.href = '#!/changeRole'
            }
        },
        /*checkWorkersPage: function(){
            this.checkUserRole()
            console.log($route.current.params.id)
        }*/
    }
}] )