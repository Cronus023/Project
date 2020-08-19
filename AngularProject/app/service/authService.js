const auth = angular.module('auth', [])
auth.factory('authService',['$http','$q','$window', function($http,  $q, $window){
    return{
        checkLogin: function(){
            const token = localStorage.getItem('JwtToken')
            $http.get(`http://localhost:8080/auth/check?token=${token}`).
            then(function (response) {
                if(response.status === 400){
                    $window.location.href = '#!/login'
                }
            })
        },
        user_navigation: function(role){
            if(role === 'PROVIDER'){
                $window.location.href = '#!/main'
                $window.location.reload()
            }
            if(role === 'CURRICULUM_REVIEWER' || role === 'REGULAR_REVIEWER' ||  role === 'SUPERVISION' ){
                $window.location.href = '#!/application/reviewers/view'
                $window.location.reload()
            }
            if(role === 'TRAINING_OPERATOR'){
                $window.location.href = '#!/trainings'
                $window.location.reload()
            }
        },

        dateFormat : function(date){
            const dateFormat = new Date(date)
            let dateString = ""
            if(dateFormat.getMinutes() < 10){
                dateString = dateFormat.getDate()+ "-" + dateFormat.getMonth()+ "-" +dateFormat.getFullYear() + ", " + dateFormat.getHours() + ":" + "0"+dateFormat.getMinutes()
            }
            else dateString = dateFormat.getDate()+ "-" + dateFormat.getMonth()+ "-" +dateFormat.getFullYear() + ", " + dateFormat.getHours() + ":" +  dateFormat.getMinutes()
            return dateString
        },
        get_user_roles : function(){
            const deferred = $q.defer()
            const login = localStorage.getItem('UserLogin')
            $http.get(`http://localhost:8080/get_roles?login=${login}`).
            then(function success(response) {
                deferred.resolve(response.data)
            })
            return deferred.promise
        },
        login:function(authBody){
            const deferred = $q.defer()

            $http.post('http://localhost:8080/login',authBody).
                then(function success(response) {
                    deferred.resolve(response.data)
                })
            return deferred.promise
        },

        registration:function(registerBody){
            const deferred = $q.defer()
            $http.post('http://localhost:8080/register',registerBody).
            then(function (response) {
                deferred.resolve(response.data.title);
            })
            return deferred.promise
        },
        logout:function(){
            const login = localStorage.getItem('UserLogin')
            $http.get(`http://localhost:8080/authLogout?login=${login}`).
            then(function (response) {
                if(response.status === 400){
                    alert("error")
                }
            })
            localStorage.removeItem('JwtToken')
            localStorage.removeItem('UserLogin')
            localStorage.removeItem('UserRole')

            $window.location.href = '#!/login'
            $window.location.reload()
        }
    }
}] )