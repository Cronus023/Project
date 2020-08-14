angular.module('myApp.application.form', [])
    .controller('FormApplicationCtrl', function($scope, $routeParams,$window, officeService) {
         $scope.navigation = 0;
         $scope.confirmData = false
         $scope.step2Status = true
         $scope.errorConfirm = ''
         $scope.typesOfReasons = [
            { key: 1, value: "DISEASE"},
            { key: 2, value: "MEDICAL_EXAMINATION" },
            { key: 3, value: "SALARY_DELAY"},
            { key: 4, value: "UNEXPECTED_BREAKDOWN" },
         ]
         $scope.application = {
            educationalProgram: "",
            groups:{
                 group02:{
                     numberOfClasses: 1,
                     activities:""
                 },
                 group24:{
                     numberOfClasses: 1,
                     activities:""
                 },
                 group65:{
                     numberOfClasses: 1,
                     activities:""
                 },
                 group6h:{
                     numberOfClasses: 1,
                     activities:""
                 },
             },
         }
         officeService.get_office_by_name($routeParams["name"]).then(function(value){
             if(value.title){
                 alert(value.title)
                 $window.location.href = '#!/main'
             }
             $scope.office = value.office
             $scope.notPassedWorkers = value.notPassedWorkers
         })
         $scope.next= function(){
             $scope.navigation +=1;
         }
         $scope.changeConfirm =function(){
             $scope.confirmData = !$scope.confirmData
         }
         $scope.back= function(){
            $scope.navigation -=1;
         }

         $scope.submit = function(){
             console.log($scope.confirmData)
             if(!$scope.confirmData){
                 $scope.errorConfirm = "Confirm your data!"
             }
             else{
                 $scope.errorConfirm =''
                 console.log($scope.office)
                 console.log($scope.application)
             }

         }

         $scope.checkData = function(){
             let flag = true
             if($scope.office.location === undefined){
                 $scope.errorMessage = "Back to step 1 and enter office location!"
                 return false
             }
             for(let i = 0; i < $scope.notPassedWorkers.length; i++){
                 if($scope.notPassedWorkers[i].reason === undefined){
                     $scope.errorMessage = "Back to step 2 and enter reasons"
                     $scope.step2Status = false
                     return false
                 }
             }
             $scope.step2Status = true
             if($scope.application.educationalProgram === ""){
                 $scope.errorMessage = "Back to step 3 and enter educational program!"
                 return false
             }
             for(let i in $scope.application.groups){
                 if($scope.application.groups[i].activities === ""){
                     $scope.errorMessage = "Back to step 3 and enter activities"
                     return false
                 }
             }
             return flag
         }
    })