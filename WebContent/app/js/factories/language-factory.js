app.factory('languageFactory', ['$cookieStore',function ($cookieStore) {
    return {
        languages: [],
        setData: function (data) {
            this.languages = data;
            $cookieStore.put('addLang', data);
        },
    getData:function(){
    	return $cookieStore.get('addLang');
    }
    };
}]);