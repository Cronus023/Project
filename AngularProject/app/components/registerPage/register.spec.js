'use strict';

describe('myApp.register module', function() {

    beforeEach(module('myApp.register'));

    describe('register controller', function(){

        it('should ....', inject(function($controller) {
            //spec body
            var LoginCtrl = $controller('RegisterCtrl');
            expect(LoginCtrl).toBeDefined();
        }));

    });
});