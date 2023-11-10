package com.productservice.service;

/**
 * @author Stephen Obi <stephen@frontedge.io>
 * @philosophy Quality must be enforced, otherwise it won't happen. We programmers must be required to write tests, otherwise we won't do it!
 * ------
 * Tip: Always code as if the guy who ends up maintaining your code will be a violent psychopath who knows where you live.
 * ------
 * @since 15/03/2022 06:53
 */


public interface ChecksumImplementation {

    @SuppressWarnings("unused")
    void generateChecksum();

    void validate();

    String digest();
}
