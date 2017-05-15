/**
 * Since Geppetto v.90
 * @author Pravin
 * Note: This modal controller will handle the activity and noun related modal operations in the project screen.
 */

/**
 * Since Geppetto 1.0
 * @author Kumaresan Perumal
 * Note: Here i added some code in createHandler method to close the modal. it depends on 'type'.
 */
app.controller('ProjectModalCtrl',
		['$scope', '$log', '$modalInstance', 'modalDataObj', 
        function($scope, $log, $modalInstance, modalDataObj){
	/**
	 * scope object
	 */
	var self=$scope;
	/**
	 * modal object
	 */
	self.modalObj={
		dataObj:{},
		message:''
	};

	/**
	 * initializing modal
	 */
	self.init=function(){		
		
		$log.debug('initializing project modal!');		
	
		var title='';
		if(modalDataObj.action=='create'){
			title='Create';
		}
		if(modalDataObj.action=='delete'){
			title='Delete';
		}
		
		if(modalDataObj.type=='activity'){
			title+=' Activity';
		}
		if(modalDataObj.type=='template'){
			title+=' Template';
		}
		if(modalDataObj.type=='noun'){
			title+=' Noun';
		}
		
		if(modalDataObj.action=='alert'){
			title=modalDataObj.title;
			self.modalObj.message=modalDataObj.message;
		}
		
		self.modalObj.title=title;
		self.modalObj.type=modalDataObj.type;
		self.modalObj.action=modalDataObj.action;
		self.modalObj.id=modalDataObj.id;
	};
	
	/**
	 * dismiss the modal
	 */
	self.close=function(){
		$modalInstance.close('dismiss');
	};
	
	/**
	 * creation action handler for the project modal
	 */
	self.createHandler=function(){
		if (self.modalObj.type == 'noun') {
			modalDataObj.createHandler(self.modalObj.type, self.modalObj.dataObj);
		}else if (self.modalObj.type == 'activity') {
			modalDataObj.createHandler(self.modalObj.type, self.modalObj.dataObj);
		}else if (self.modalObj.type == 'template') {
			modalDataObj.createHandler(self.modalObj.type, self.modalObj.dataObj);
			self.close();
		 }
	};
	
	
	/**
	 * delete handler for the project modal
	 */
	self.deleteHandler=function(){
		modalDataObj.deleteHandler(self.modalObj.type);
		self.close();
	};
	
	
	self.init();
}]);