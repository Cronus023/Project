'use strict';
describe('myApp.application.reviewers module', function() {
    beforeEach(module('myApp.application.reviewers'))
    describe('application.reviewers controller', function(){
        it('should ....', inject(function($controller) {
            //spec body
            const LoginCtrl = $controller('ReviewersApplicationCtrl')
            expect(LoginCtrl).toBeDefined()
        }))
    })
})