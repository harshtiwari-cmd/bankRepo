-- Ensure table exists and contact has bigint type
CREATE TABLE IF NOT EXISTS bank_details (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    mail VARCHAR(255),
    contact BIGINT,
    international_contact VARCHAR(255),
    insta_url VARCHAR(512),
    twitter_url VARCHAR(512),
    fb_url VARCHAR(512),
    dukhen_bank_url VARCHAR(512),
    snap_chat_url VARCHAR(512),
    you_tube_url VARCHAR(512),
    name_en VARCHAR(255),
    name_ar VARCHAR(255),
    url_en VARCHAR(512),
    url_ar VARCHAR(512),
    display_order INTEGER,
    follow_us_json TEXT
);

-- Ensure new columns exist on legacy databases
ALTER TABLE bank_details ADD COLUMN IF NOT EXISTS name_en VARCHAR(255);
ALTER TABLE bank_details ADD COLUMN IF NOT EXISTS name_ar VARCHAR(255);
ALTER TABLE bank_details ADD COLUMN IF NOT EXISTS url_en VARCHAR(512);
ALTER TABLE bank_details ADD COLUMN IF NOT EXISTS url_ar VARCHAR(512);
ALTER TABLE bank_details ADD COLUMN IF NOT EXISTS display_order INTEGER;
ALTER TABLE bank_details ADD COLUMN IF NOT EXISTS follow_us_json TEXT;

-- Note: Column type adjustments should be handled via a one-off migration or manually.
