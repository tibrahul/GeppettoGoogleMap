    app.factory('authFactory', ['$cookieStore', function ($cookieStore) {

        var _user = {};

        return {

            user : _user,

            setUser: function (_user) {
                existing_cookie_user = $cookieStore.get('current.user');
                _user =  _user || existing_cookie_user;
                $cookieStore.put('current.user', _user);
            },

            getUser:function(){
            	return $cookieStore.get('current.user');
            },
            removeUser: function () {
                $cookieStore.remove('current.user', _user);
            }
        };
    }])
;