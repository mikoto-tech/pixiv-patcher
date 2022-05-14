package net.mikoto.pixiv.patcher;

import net.mikoto.pixiv.forward.connector.exception.GetArtworkInformationException;
import net.mikoto.pixiv.forward.connector.exception.GetImageException;
import net.mikoto.pixiv.forward.connector.exception.WrongSignException;
import net.mikoto.pixiv.patcher.exception.AlreadyStartedException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;

@SpringBootTest
class PixivPatcherApplicationTests {

    @Test
    void patcherTest() throws NoSuchMethodException, IOException, GetImageException, GetArtworkInformationException, InvalidKeySpecException, NoSuchAlgorithmException, SignatureException, InvalidKeyException, WrongSignException, IllegalAccessException, AlreadyStartedException {
    }

}
