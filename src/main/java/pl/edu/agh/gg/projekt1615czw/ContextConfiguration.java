package pl.edu.agh.gg.projekt1615czw;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.edu.agh.gg.projekt1615czw.application.bitmap.BitmapProvider;

import java.io.IOException;

@Configuration
public class ContextConfiguration {
    @Bean
    @Autowired
    public BitmapProvider bitmapProvider(@Value("${resources.bitmap.path}") String bitmapResourcePath) throws IOException {
        return new BitmapProvider(bitmapResourcePath);
    }
}
