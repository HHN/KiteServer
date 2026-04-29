package com.hhn.kite2server.data;

import lombok.*;

/**
 * Request record for removing a data object by its unique identifier.
 * @param id The database ID of the object to be deleted.
 */
public record RemoveDataObjectRequest(
    long id
){}
