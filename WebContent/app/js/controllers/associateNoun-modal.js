/**
 * Since Geppetto v.90
 * Created: July 15, 2015
 * @author Pravin
 * 
 * Note: This controller will handle all the modal's related to the activity
 */

app.controller('associateNounCtrl',
		['$scope', '$log', '$modalInstance', 'data','$http','RestURL','$filter','ActivityInformation',
		 function($scope, $log, $modalInstance, data,$http,RestURL,$filter,ActivityInfo){
			
	var self=$scope;
	
	/**
	 * reference for external scope object for ui grid 
	 */
	self.scope=$scope;
	self.modalData={};
	/**
	 * close handler for modal
	 */
	self.close=function(){
		  $modalInstance.dismiss('cancel');	
	};	
	
	self.init = function(){
		
		
		self.modalData.primary_Noun_name = ActivityInfo.activity.primary_noun.name;
		self.modalData.primary_Noun = ActivityInfo.activity.primary_noun;
		self.modalData.nounattributes = ActivityInfo.activity.primary_noun.nounattributes;
	          if(data.associateNounData){
	        	  //self.modalData.primary_Noun = data.associateNounData.primary_noun;
	        	  self.modalData.pn_attribute = data.associateNounData.pn_attribute;
	          }
	}
	
	$scope.choosePnAttribute = function(){
		for (var i = 0; i < $scope.modalData.nounattributes.length; i++) {
	      if ($scope.modalData.nounattributes[i]['id'] == $scope.modalData.pn_attribute_id) {
	    	  $scope.modalData.pn_attribute = $scope.modalData.nounattributes[i];
	      }
	    }
	}
	self.ok = function (){
		if(self.modalData.primary_Noun == null || self.modalData.primary_Noun == undefined){
			return
		}
		$modalInstance.close(self.modalData);	
	}
	
	self.init();
	
}])