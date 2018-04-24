/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function clearText(id) {
    $("#" + id +" textarea").each( function(el) {
        $(this).val('');
    });

    $("#"+id+" input:text").each( function(el) {
        $(this).val('');
    });

    $("#" + id +" select").each( function(el) {
        $(this).val('');
    });

    $("#"+id+" input:password").each( function(el) {
        $(this).val('');
    });
}
