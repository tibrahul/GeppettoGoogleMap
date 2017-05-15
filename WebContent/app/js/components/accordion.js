var previousAccordionPanel;

$(document).on('click', '.accordion._Gp_element .panel .panel-heading a', function (e) {
    e.preventDefault();

    var panel = $(this).data('ng-href');
    var parent = $(panel).parents('.panel');

    /* adjust height of container */
    $(panel).parents('.accordion').css('height', 'auto');
    $(panel).parents('.resizable').css('height', 'auto');

    /* toggle panels */
    parent.toggleClass('active').siblings().removeClass('active');

    /* make panels same height */
    if (previousAccordionPanel)  {
        if ($(previousAccordionPanel)[0] != undefined && $(previousAccordionPanel)[0].style.height !== '') {
            $(previousAccordionPanel).parents('.panel').siblings().find('.section').each(function (index, node) {
                //$(node).css('height', $(previousAccordionPanel)[0].style.height);
            });
        }
    }
    previousAccordionPanel = $(this).data('ng-href');

    /* show children of current panel and hide siblings children */
    var screenChildren = nodeTree['screen']['children'];
    var accordion = $(panel).parents('.resizable').attr('id');
    var firstLevel, secondLevel;

    if (screenChildren.hasOwnProperty(accordion)) {
        if (screenChildren[accordion].hasOwnProperty('children')) {
            for (firstLevel in screenChildren[accordion]['children']) {
                if (screenChildren[accordion]['children'].hasOwnProperty(firstLevel)) {
                    if (screenChildren[accordion]['children'][firstLevel].hasOwnProperty('children')) {
                        if (firstLevel === panel.substr(1) && parent.hasClass('active')) {
                            for (secondLevel in screenChildren[accordion]['children'][firstLevel]['children']) {
                                if (screenChildren[accordion]['children'][firstLevel]['children'].hasOwnProperty(secondLevel)) {
                                    $('#' + secondLevel).show();
                                }
                            }
                        } else {
                            for (secondLevel in screenChildren[accordion]['children'][firstLevel]['children']) {
                                if (screenChildren[accordion]['children'][firstLevel]['children'].hasOwnProperty(secondLevel)) {
                                    $('#' + secondLevel).hide();
                                }
                            }
                        }
                    }
                }
            }
        }
    }
});