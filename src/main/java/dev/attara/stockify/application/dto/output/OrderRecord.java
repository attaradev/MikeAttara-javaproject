package dev.attara.stockify.application.dto.output;

import java.util.List;

public record OrderRecord(

        long id,

        UserRecord user,

        List<ProductLineRecord> productLines

) { }
