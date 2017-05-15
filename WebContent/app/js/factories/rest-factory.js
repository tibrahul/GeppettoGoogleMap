// Date Created :  April 14 2015
// @author Vijay
// Purpose of this controller to update the existing noun and noun attributes.
// since .75

app.factory('RestURL', function () {
  return {
	//For testing locally
	 baseURL: 'http://localhost:8080/geppetto_appbuilder/',
	//DEV
      // baseURL: 'http://dev.geppettosoftware.com/geppetto_appbuilder/',
	//STAGE
    //baseURL: 'http://stage.geppettosoftware.com/geppetto_appbuilder/',
    	
   	//Node URL NEW USER LIST -> DEV
   	nodeURL :'http://52.207.24.87:8030/'
   	
   	//Node URL NEW USER LIST -> STAGE
   //	nodeURL  : 'http://52.206.208.247:8030/'
    	
  }
});	
